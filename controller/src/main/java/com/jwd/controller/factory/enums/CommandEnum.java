package com.jwd.controller.factory.enums;


import com.jwd.controller.command.Command;
import com.jwd.controller.command.impl.*;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommandImpl();
        }
    },
    REGISTRATION {
        {
            this.command = new RegistrationCommandImpl();
        }
    },
    SHOW_ALL_SERVICES {
        {
            this.command = new FindAllServicesImpl();
        }
    },
    CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageImpl();
        }
    },
    ADD_SERVICE_ORDER {
        {
            this.command = new AddServiceOrderImpl();
        }
    },
    WORK {
        {
            this.command = new WorkImpl();
        }
    },
    SHOW_USER_ORDERS {
        {
            this.command = new FindUserOrdersImpl();
        }
    }
    ;


    Command command;


    public Command getCommand()
    {
        return command;
    }
}
