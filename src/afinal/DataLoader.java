package afinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class DataLoader {


    static double getPositionSize (Path file){
        ArrayList<Double> data = new ArrayList<>();

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            reader.readLine(); //remove header
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                data.add(Double.parseDouble(arr[4]));
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return data.get(data.size()-1);
    }

    static double getEntryPosition (Path file){
        ArrayList<Double> data = new ArrayList<>();

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            reader.readLine(); //remove header
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                data.add(Double.parseDouble(arr[3]));
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return data.get(data.size()-1);
    }

    static double getBal(Path file){

        ArrayList<Double> data = new ArrayList<>();

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            reader.readLine(); //remove header
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                data.add(Double.parseDouble(arr[2]));
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return data.get(data.size()-1);
    }

    static int getSpeed(Path file){

        ArrayList<Integer> data = new ArrayList<>();

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            reader.readLine(); //remove header
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                data.add(Integer.parseInt(arr[0]));
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        return data.get(data.size()-1);
    }
    static double getZoom(Path file){

        ArrayList<Double> data = new ArrayList<>();

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            reader.readLine(); //remove header
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                data.add(Double.parseDouble(arr[1]));
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        return data.get(data.size()-1);
    }


    static ArrayList<Double> getData(Path file){

        ArrayList<Double> data = new ArrayList<Double>();

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            reader.readLine(); //remove header
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                data.add(Double.parseDouble(arr[0]));
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        return data;
    }
    static ArrayList<Long> getTime(Path file){

        ArrayList<Long> data = new ArrayList<Long>();

        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader =
                     new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            reader.readLine(); //remove header
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(",");
                data.add(Long.parseLong(arr[1]));
            }
        } catch (IOException x) {
            System.err.println(x);
        }

        return data;
    }
}
