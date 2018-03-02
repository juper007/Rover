package com.rover.juper007;

import java.io.*;

public class ImportData {
    private static final String DATA_FILE_DELIMITER = ",";

    public static void main(String args[]) {
        if (args.length < 1) {
            System.out.println("Please provide Data file name.");
            System.out.println(">> java ImportData [FileName]");
            System.exit(1);
        }

        File fileName = new File(args[0]);
        if (!fileName.exists()) {
            System.out.println(args[0] + " file not found. Please provide valid Data file name.");
            System.exit(1);
        }

        DB_Manager dbm = new DB_Manager();

        try {
            FileReader fileReader = new FileReader(fileName.getName());
            BufferedReader bufferReader = new BufferedReader(fileReader);

            bufferReader.readLine(); // Skip Column Header
            String line = bufferReader.readLine();
            while (line != null) {
                String[] data = line.split(DATA_FILE_DELIMITER);

                User user = dbm.insertUserInfo(data[7], data[4], data[11], data[12]);
                Sitter sitter = dbm.insertSitterInfo(data[6], data[1], data[9], data[10]);
                Sitting sitting = dbm.insertSittingInfo(user.id, sitter.id, data[8], data[2], data[3], data[0]);


                String[] pets = data[5].split("\\|");
                for (int i = 0; i < pets.length; i++) {
                    Pet pet = dbm.insertPetInfo(user.id, pets[i]);
                    dbm.insertPetList(sitting.sittingId, pet.id);
                }

                line = bufferReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

