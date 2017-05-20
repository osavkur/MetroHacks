import java.io.*;
 
class PythonToJava{
    public static void main(String a[]){
        try{
            String pythonScript = "";
            ProcessBuilder pb = new ProcessBuilder("python",pythonScript);
            Process p = pb.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int ret = new Integer(in.readLine()).intValue();
            System.out.println("value is : "+ret);
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}