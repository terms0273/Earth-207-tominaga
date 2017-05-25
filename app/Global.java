import play.*;
import play.libs.*;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;

public class Global extends GlobalSettings{
  @Override
  public <T extends EssentialFilter> Class<T>[] filters(){
    return new Class[]{
      CSRFFilter.class
    };
  }
}
