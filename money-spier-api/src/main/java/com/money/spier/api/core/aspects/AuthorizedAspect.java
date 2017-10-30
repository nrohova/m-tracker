package com.money.spier.api.core.aspects;

import com.money.spier.api.core.entities.Permission;
import com.money.spier.api.core.entities.User;
import com.money.spier.api.core.exceptions.NoPermissions;
import com.money.spier.api.infrastructure.database.UserRepository;
import java.util.List;
import java.util.Optional;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuthorizedAspect {

  @Autowired
  private UserRepository userRepository;

  @Pointcut(value = "@annotation(authorized)", argNames = "authorized")
  public void isUserAuthorized(Authorized authorized) {}

  @Pointcut("execution(public * * (.., @UserName (*), ..))")
  public void currentUserName() {}

  @Around("isUserAuthorized(authorized) && currentUserName()")
  public Object process(ProceedingJoinPoint joinPoint, Authorized authorized) {
    Object[] args = joinPoint.getArgs();
    String userNameArg = (String) args[0];

    User user = userRepository.getByUserName(userNameArg);
    if (user != null) {
      List<Permission> permissions = user.getPermissions();

      Optional<Permission> accumulatedPermission = permissions.stream().reduce((p1, p2) -> {
        Permission result = new Permission();
        result.setExpenseWrite(p1.isExpenseWrite() || p2.isExpenseWrite());
        result.setExpenseRead(p1.isExpenseRead() || p2.isExpenseRead());
        result.setIncomeWrite(p1.isIncomeWrite() || p2.isIncomeWrite());
        result.setIncomeRead(p1.isIncomeRead() || p2.isIncomeRead());
        return result;
      });

      if (!accumulatedPermission.isPresent()) {
        throw new NoPermissions("No such user or permissions");
      }

      Permission permission = accumulatedPermission.get();
      if (isMatchPermission(authorized, permission)) {
        try {
          return joinPoint.proceed();
        } catch (Throwable throwable) {
          throwable.printStackTrace();
        }
      }
    }

    throw new NoPermissions("No permissions");
  }

  private boolean isMatchPermission(Authorized authorized, Permission permission) {
    return hasPermissionIfExpected(authorized.readExpenses(), permission.isExpenseRead())
        && hasPermissionIfExpected(authorized.writeExpenses(), permission.isExpenseWrite())
        && hasPermissionIfExpected(authorized.readIncomes(), permission.isIncomeRead())
        && hasPermissionIfExpected(authorized.writeIncomes(), permission.isExpenseRead());
  }

  private boolean hasPermissionIfExpected(boolean expected, boolean actual) {
    return !expected || actual;
  }

}
