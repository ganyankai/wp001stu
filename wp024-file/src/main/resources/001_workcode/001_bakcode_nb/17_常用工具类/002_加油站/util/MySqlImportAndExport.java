package util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by houhuateng on 2015/12/25.
 */
public class MySqlImportAndExport {

    public static void main(String args[]) throws IOException {
//        InputStream is = MySqlImportAndExport.class.getClassLoader().getResourceAsStream("jdbc.properties");
//        Properties properties = new Properties();
//        properties.load(is);
      //MySqlImportAndExport.export();//这里简单点异常我就直接往上抛
        MySqlImportAndExport.importSql();
    }

    /**
     * 根据属性文件的配置导出指定位置的指定数据库到指定位置
     * @param importDatabaseName
     * @throws IOException
     */
    public static void export() throws IOException {
        Runtime runtime = Runtime.getRuntime();
        String command = getExportCommand();
        System.out.println("===" + command);
        runtime.exec(command);//这里简单一点异常我就直接往上抛
    }

    /**
     * 根据属性文件的配置把指定位置的指定文件内容导入到指定的数据库中
     * 在命令窗口进行mysql的数据库导入一般分三步走：
     * 第一步是登到到mysql； mysql -uusername -ppassword -hhost -Pport -DdatabaseName;如果在登录的时候指定了数据库名则会
     * 直接转向该数据库，这样就可以跳过第二步，直接第三步；
     * 第二步是切换到导入的目标数据库；use importDatabaseName；
     * 第三步是开始从目标文件导入数据到目标数据库；source importPath；
     * @throws IOException
     */
    public static void importSql() throws IOException {
        //2015/12/25会有阻塞
        Runtime runtime = Runtime.getRuntime();
        //因为在命令窗口进行mysql数据库的导入一般分三步走，所以所执行的命令将以字符串数组的形式出现
        String cmdarray[] = getImportCommand();//根据属性文件的配置获取数据库导入所需的命令，组成一个数组
        //runtime.exec(cmdarray);//这里也是简单的直接抛出异常
        System.out.print(cmdarray[0]);
        Process process = runtime.exec(cmdarray[0]);
        //执行了第一条命令以后已经登录到mysql了，所以之后就是利用mysql的命令窗口
        //进程执行后面的代码
        OutputStream os = process.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os);
        //命令1和命令2要放在一起执行
        System.out.print(cmdarray[1]);
        System.out.print(cmdarray[2]);
        writer.write(cmdarray[1] + "\r\n" + cmdarray[2]);
        writer.flush();
        writer.close();
        os.close();
    }

    /**
     * 利用属性文件提供的配置来拼装命令语句
     * 在拼装命令语句的时候有一点是需要注意的：一般我们在命令窗口直接使用命令来
     * 进行导出的时候可以简单使用“>”来表示导出到什么地方，即mysqldump -uusername -ppassword databaseName > exportPath，
     * 但在Java中这样写是不行的，它需要你用-r明确的指出导出到什么地方，如：
     * mysqldump -uusername -ppassword databaseName -r exportPath。
     * @param properties
     * @return
     */
    private static String getExportCommand() {
        StringBuffer command = new StringBuffer();
        String username = "root";//用户名
        String password = "2015eid";//密码
        String exportDatabaseName = "mroil";//需要导出的数据库名
        String host = "172.168.63.190";//导入的目标数据库所在的主机
        String port = "3306";//使用的端口号
        String exportPath = "d:\\1111.sql";//导出路径

        //注意哪些地方要空格，哪些不要空格
        command.append("mysqldump -u").append(username).append(" -p").append(password)//密码是用的小p，而端口是用的大P。
                .append(" -h").append(host).append(" -P").append(port).append(" ").append(exportDatabaseName).append(" -r ").append(exportPath);
        return command.toString();
    }

    /**
     * 根据属性文件的配置，分三步走获取从目标文件导入数据到目标数据库所需的命令
     * 如果在登录的时候指定了数据库名则会
     * 直接转向该数据库，这样就可以跳过第二步，直接第三步；
     * @param properties
     * @return
     */
    private static String[] getImportCommand() {
        String username = "root";//用户名
        String password = "2015eid";//密码
        String host = "172.168.63.190";//导入的目标数据库所在的主机
        String port = "3306";//使用的端口号
        String importDatabaseName = "mroil";//导入的目标数据库的名称
        String importPath = "d:\\1111.sql";//导入的目标文件所在的位置
        //第一步，获取登录命令语句
        String loginCommand = new StringBuffer().append("mysql -u").append(username).append(" -p").append(password).append(" -h").append(host)
                .append(" -P").append(port).toString();
        //第二步，获取切换数据库到目标数据库的命令语句
        String switchCommand = new StringBuffer("use ").append(importDatabaseName).toString();
        //第三步，获取导入的命令语句
        String importCommand = new StringBuffer("source ").append(importPath).toString();
        //需要返回的命令语句数组
        String[] commands = new String[] {loginCommand, switchCommand, importCommand};
        return commands;
    }
}
