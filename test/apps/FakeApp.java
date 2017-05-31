package apps;

import com.avaje.ebean.Ebean;
import net.sf.ehcache.*;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.test.FakeApplication;
import java.io.IOException;

import static play.test.Helpers.*;
public class FakeApp{
  public static FakeApplication app;
  public static String createDd1 = "";
  public static String dropDd1 ="";

  @BeforeClass
  public static void startApp() throws IOException{
    app = fakeApplication(inMemoryDatabase());
    start(app);
    String evolutionContent = FileUtils.readFileToString(app.getWrappedApplication()
  .getFile("conf/evolutions/default/1.sql"));
    String[] splitEvolutionContent = evolutionContent.split("# --- !Ups");
    String[] upsDowns = splitEvolutionContent[1].split("# --- !Downs");
    createDd1 = upsDowns[0];
    dropDd1 = upsDowns[1];
  }

  @Before
  public void createCleanDd(){
    initDd();

  }

  public static void initDd(){
    Ebean.execute(Ebean.createCallableSql(dropDd1));
    Ebean.execute(Ebean.createCallableSql(createDd1));
    //Encacheキャッシュクリア
    CacheManager manager = CacheManager.create();
    Cache cache = manager.getCache("play");
    cache.removeAll();
  }

  public static void restartApp(){
    start(app);
  }

  @AfterClass
  public static void stopApp(){
    stop(app);
  }

}
