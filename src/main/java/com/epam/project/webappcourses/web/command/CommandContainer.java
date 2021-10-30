package com.epam.project.webappcourses.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class CommandContainer {

    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        commands.put("login", new LoginCommand());
        commands.put("updateJournal", new UpdateJournalCommand());
        commands.put("search", new SearchCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("addcourse",new AddCourseCommand());
        commands.put("addteacher",new AddTeacherCommand());
        commands.put("changeStatus", new ChangeStatusCommand());
        commands.put("changeLanguage", new ChangeLanguageCommand());
        commands.put("attachCourse", new AttachCourseCommand());
        commands.put("registerForCourse", new CourseRegisterCommand());



        log.debug("Command container was successfully initialized");
        log.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName
     *            Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }


}
