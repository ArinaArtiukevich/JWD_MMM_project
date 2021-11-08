package com.jwd.controller.factory.enums;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.impl.*;

public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommandImpl();
        }
    },
    LOGOUT{
        {
            this.command = new LogoutImpl();
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
    },
    FIND_ORDER_INFO {
        {
            this.command = new FindOrderInfoImpl();
        }
    },
    TAKE_ORDER {
        {
            this.command = new TakeOrderImpl();
        }
    },
    CLOSE_ORDER {
        {
            this.command = new CloseOrderImpl();
        }
    },
    APPROVE_ORDER {
        {
            this.command = new ApproveOrderImpl();
        }
    },
    FIND_WORKER_RESPONSE {
        {
            this.command = new FindWorkerResponseImpl();
        }
    },
    FIND_CLIENT_RESPONSE {
        {
            this.command = new FindClientResponseImpl();
        }
    },
    SHOW_ORDERS_BY_SERVICE_TYPE {
        {
            this.command = new FindOrdersByServiceTypeImpl();
        }
    }
    ;

    Command command;


    public Command getCommand()
    {
        return command;
    }
}
