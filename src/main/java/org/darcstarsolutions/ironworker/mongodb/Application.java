package org.darcstarsolutions.ironworker.mongodb;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.darcstarsolutions.ironworker.mongodb.configuration.TimeStampRepository;
import org.darcstarsolutions.ironworker.mongodb.entities.TimeStamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Created by mharris on 5/27/15.
 */

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private TimeStampRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static String readFile(String path) throws IOException {
        FileInputStream stream = new FileInputStream(new File(path));
        try {
            FileChannel chan = stream.getChannel();
            MappedByteBuffer buf = chan.map(FileChannel.MapMode.READ_ONLY, 0, chan.size());
            return Charset.defaultCharset().decode(buf).toString();
        } finally {
            stream.close();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        int payloadPosition = getPayLoadPosition(args);
        if ((payloadPosition < 0) || (payloadPosition >= args.length)) {
            System.err.println("Invalid Payload argument");
            System.exit(1);
        } else {
            String payload = readFile(args[payloadPosition]);
            Gson gson = new Gson();
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(payload).getAsJsonObject();

            String idString = gson.fromJson(jsonObject.get("id"), String.class);
            BigInteger id = new BigInteger(idString, 16);
            System.out.println("Id: " + id.toString());
            TimeStamp timeStamp = repository.findOne(id);
            timeStamp.setRead(true);
            repository.save(timeStamp);
            System.out.println(timeStamp.toString());
        }
    }

    private int getPayLoadPosition(String[] args) {
        int payloadPos = -1;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-payload")) {
                payloadPos = i + 1;
                break;
            }
        }
        return payloadPos;
    }
}
