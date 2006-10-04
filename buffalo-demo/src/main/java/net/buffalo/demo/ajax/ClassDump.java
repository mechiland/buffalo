package net.buffalo.demo.ajax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author vincentx
 */
public class ClassDump {
		private static String[] classes = null;
       private static String[] dumpFromDir(String dir) throws Exception {
               ArrayList result = new ArrayList();
               File classesPath = new File(dir);
               if (!classesPath.exists())
                       throw new IOException();
               String[] paths = dumpFromDir(classesPath);
               for (int i = 0; i < paths.length; i++) {
            	   String path = paths[i];
            	   result.add(path.substring(dir.length()));
               }
               return (String[])result.toArray(new String[result.size()]);
       }

       private static String[] dumpFromDir(File dir) throws Exception {
               ArrayList result = new ArrayList();
               String[] paths = dir.list();
               for (int i = 0; i < paths.length; i++) {
            	   String path = paths[i];
                       File file = new File(dir, path);
                       if (file.isDirectory())
                               result.addAll(Arrays.asList(dumpFromDir(file)));
                       else if (path.endsWith(".class") && 
                    		   path.indexOf("$")==-1 && 
            		   ( path.startsWith("java") ||
            				   path.startsWith("javax"))) {
                               result.add(file.getAbsolutePath());
                       }

               }
               return (String[])result.toArray(new String[result.size()]);
       }

       private static String[] dumpFromJar(String jar) throws Exception {
               ArrayList result = new ArrayList();
               System.out.println("dump : " + jar);
               Enumeration entryItr = new ZipFile(jar).entries();
               while (entryItr.hasMoreElements()) {
                       ZipEntry entry = (ZipEntry) (entryItr.nextElement() );
                       if (entry.getName().endsWith(".class") && 
                    		   entry.getName().indexOf("$")==-1 && 
                    		   ( entry.getName().startsWith("java") ||
                    		   entry.getName().startsWith("javax"))) {
                    	   result.add(entry.getName().replace('/', '.').replaceAll("\\.class",""));
                       }
               }
               return (String[])result.toArray(new String[result.size()]);
       }

       public static void main(String[] args) throws Exception {
               ArrayList pathes = new ArrayList();
               pathes.addAll(Arrays.asList(System.getProperty("java.class.path")
                               .split(";")));
               pathes.addAll(Arrays.asList(System.getProperty("sun.boot.class.path")
                               .split(";")));
               ArrayList result = new ArrayList();
               String[] paths = (String[])pathes.toArray(new String[pathes.size()]);
               for (int i = 0; i < paths.length; i++) {
            	   String path = paths[i];
                       try {
                               result.addAll(Arrays.asList(path.endsWith(".jar") ? dumpFromJar(path)
                                                               : dumpFromDir(path)));
                       } catch (Exception e) {}
               }
       }
       
       public static String[] classesList() {
    	     if (classes != null) {
    	    	 return classes;
    	     }
    	     
    	     ArrayList pathes = new ArrayList();
             pathes.addAll(Arrays.asList(System.getProperty("java.class.path")
                             .split(";")));
             pathes.addAll(Arrays.asList(System.getProperty("sun.boot.class.path")
                             .split(";")));
             ArrayList result = new ArrayList();
             String[] paths = (String[])pathes.toArray(new String[pathes.size()]);
             for (int i = 0; i < paths.length; i++) {
          	   String path = paths[i];
                     try {
                         result.addAll(Arrays.asList(path.endsWith(".jar") ? dumpFromJar(path)
                                                             : dumpFromDir(path)));
                     } catch (Exception e) {}
             }
             
             classes = (String[])result.toArray(new String[result.size()]);
             
             return classes;
       }
}