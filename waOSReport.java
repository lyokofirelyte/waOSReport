package com.github.lyokofirelyte.waOSReport;


import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class waOSReport extends JavaPlugin {


	
	Connection c = null;
	
	Logger log = Logger.getLogger("Minecraft");


    public void onEnable(){ 
	log.info("Enabled waOS Reporting!");
	getCommand("report").setExecutor(new Commands());
	

	MySQL MySQL = new MySQL("localhost", 3306, "WA2", "root", "_O13piKm");
	c = MySQL.open();
}
 
    public void onDisable(){ 
		log.info("Disabling waOS Reporting!");
}
    public class Commands implements CommandExecutor {
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
            if(cmd.getName().equalsIgnoreCase("report")){
                Player player = (Player) sender;
                String pName = player.getName();
            
                Connection c = null;
                
                try {

                 
                    if(!(args.length == 0)){
                            MySQL MySQL = new MySQL("localhost", 3306, "WA2", "root", "_O13piKm");
                            c = MySQL.open();
                            Statement statement = c.createStatement();
                            ResultSet res = statement.executeQuery("SELECT * FROM users WHERE minecraft = '" + pName + "';");
                            res.next();
                         
                            if(res.getString("minecraft") == null){
                                String UserName = args[0];
                                String Report = args[1];
                              
                             
                                statement.executeUpdate("INSERT INTO users ('minecraft', 'username', 'report') VALUES('" + pName + "', '" + UserName + "', '" + Report + "');");
                                player.sendMessage("[ waOS ] Your report has been received! " + UserName + " Report: " + Report);
                            }else{
                                player.sendMessage("[ waOS ] Something went wrong! Contact a System Administrator!");
                            }
                        }else{
                            player.sendMessage("[ waOS ] Something went wrong! Contact a System Administrator!");

                    }
                            } catch (SQLException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        return false;
                    }
    }
}


