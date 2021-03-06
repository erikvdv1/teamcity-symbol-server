package jetbrains.buildServer.symbols;

import jetbrains.buildServer.BaseTestCase;
import jetbrains.buildServer.agent.NullBuildProgressLogger;
import jetbrains.buildServer.symbols.tools.JetSymbolsExe;
import jetbrains.buildServer.util.FileUtil;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author Evgeniy.Koshkin
 */
public class JetSymbolsExeTest extends BaseTestCase {

  private JetSymbolsExe myExe;

  @Override
  @BeforeMethod
  public void setUp() throws Exception {
    super.setUp();
    File homeDir = new File("jet-symbols\\out").getAbsoluteFile();
    assertTrue("Failed to find JetSymbolsExe home dir on path " + homeDir.getAbsolutePath(), homeDir.isDirectory());
    myExe = new JetSymbolsExe(homeDir);
  }

  @Test
  public void testCmdParametersLengthLimit() throws Exception {
    final File output = FileUtil.createTempFile("testCmdParametersLengthLimit", ".out");
    final int dumpExitCode = myExe.dumpGuidsToFile(getFilesCollection(500), output, new NullBuildProgressLogger());
    assertEquals(0, dumpExitCode);
  }

  @Test
  public void testSpacesInPaths() throws Exception {
    final File output = FileUtil.createTempFile("test spaces in paths", ".out");
    final File input = FileUtil.createTempFile("test spaces in paths", ".in");
    final int exitCode = myExe.dumpGuidsToFile(Collections.singleton(input), output, new NullBuildProgressLogger());
    assertEquals(0, exitCode);
  }

  private Collection<File> getFilesCollection(int count) throws IOException {
    Collection<File> result = new HashSet<File>();
    for (int i = 0; i < count; i++){
      result.add(FileUtil.createTempFile("foo", "boo"));
    }
    return result;
  }
}
