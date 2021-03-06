package org.sobotics.guttenberg.clients;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sobotics.guttenberg.roomdata.BotRoom;
import org.sobotics.guttenberg.roomdata.SOBoticsChatRoom;
import org.sobotics.guttenberg.utils.FilePathUtils;
import org.sobotics.guttenberg.utils.StatusUtils;

import fr.tunaki.stackoverflow.chat.StackExchangeClient;


/**
 * The main class
 * */
public class Client {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        LOGGER.info("Hello, World!");
        LOGGER.info("Load properties...");
        
        Properties prop = new Properties();

        try{
            prop.load(new FileInputStream(FilePathUtils.loginPropertiesFile));
        }
        catch (IOException e){
            e.printStackTrace();
            LOGGER.error("Error: ", e);
            LOGGER.error("Could not load login.properties! Shutting down...");
            return;
        }
        
        LOGGER.info("Initialize chat...");
        StackExchangeClient seClient = new StackExchangeClient(prop.getProperty("email"), prop.getProperty("password"));
        
        List<BotRoom> rooms = new ArrayList<>();
        rooms.add(new SOBoticsChatRoom());
        
        LOGGER.info("Launch Guttenberg...");
        
        Guttenberg guttenberg = new Guttenberg(seClient, rooms);
        
        guttenberg.start();
        
        StatusUtils.startupDate = Instant.now();
        LOGGER.info(StatusUtils.startupDate + " - Successfully launched Guttenberg!");
    }

}
