package com.intellij.openapi.externalSystem.model.project;

import com.intellij.openapi.roots.DependencyScope;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * @author Denis Zhdanov
 * @since 8/10/11 6:41 PM
 */
public abstract class AbstractDependencyData<T extends AbstractExternalEntityData & Named> extends AbstractExternalEntityData
  implements DependencyData, Named
{

  private static final long serialVersionUID = 1L;

  @NotNull private final ModuleData myOwnerModule;
  @NotNull private final T          myTarget;

  private DependencyScope myScope = DependencyScope.COMPILE;

  private boolean myExported;

  protected AbstractDependencyData(@NotNull ModuleData ownerModule, @NotNull T dependency) {
    super(ownerModule.getOwner());
    myOwnerModule = ownerModule;
    myTarget = dependency;
  }

  @NotNull
  public ModuleData getOwnerModule() {
    return myOwnerModule;
  }

  @NotNull
  public T getTarget() {
    return myTarget;
  }

  @Override
  @NotNull
  public DependencyScope getScope() {
    return myScope;
  }

  public void setScope(DependencyScope scope) {
    myScope = scope;
  }

  @Override
  public boolean isExported() {
    return myExported;
  }

  public void setExported(boolean exported) {
    myExported = exported;
  }

  @NotNull
  @Override
  public String getName() {
    return myTarget.getName();
  }
  
  @Override
  public void setName(@NotNull String name) {
    myTarget.setName(name);
  }

  @SuppressWarnings("MethodOverridesPrivateMethodOfSuperclass")
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + myOwnerModule.hashCode();
    result = 31 * result + myTarget.hashCode();
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (!super.equals(o)) {
      return false;
    }
    AbstractDependencyData<?> that = (AbstractDependencyData<?>)o;
    return myOwnerModule.equals(that.myOwnerModule) && myTarget.equals(that.myTarget);
  }

  @Override
  public String toString() {
    return "dependency=" + getTarget() + "|scope=" + getScope() + "|exported=" + isExported() + "|owner=" + getOwnerModule();
  }
}
