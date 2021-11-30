package com.jwd.controller.factory.enumType;

import com.jwd.controller.command.Command;
import com.jwd.controller.command.impl.*;

public enum CommandEnum {
    LOGIN(new LoginCommandImpl()),
    LOGOUT(new LogoutCommandImpl()),
    REGISTRATION(new RegistrationCommandImpl()),
    CHANGE_LANGUAGE(new ChangeLanguageCommandImpl()),
    ADD_SERVICE_ORDER(new AddServiceOrderCommandImpl()),
    WORK(new WorkImpl()),
    FIND_ORDER_INFO(new FindOrderInfoCommandImpl()),
    TAKE_ORDER(new TakeOrderCommandImpl()),
    CLOSE_ORDER(new CloseOrderCommandImpl()),
    APPROVE_ORDER(new ApproveOrderCommandImpl()),
    FIND_WORKER_RESPONSE(new FindWorkerResponseCommandImpl()),
    FIND_CLIENT_RESPONSE(new FindClientResponseCommandImpl()),
    SHOW_ORDERS_BY_SERVICE_TYPE(new FindOrdersByServiceTypeCommandImpl()),
    UPDATE_USER(new UpdateUserCommandImpl()),
    FIND_USER_INFORMATION(new FindUserInformationCommandImpl()),
    GO_TO_PAGE(new GoToPageCommandImpl()),
    FIND_CLIENT_ORDER_BY_STATUS(new FindClientOrderByStatusCommandImpl()),
    DELETE_ORDER_BY_ID(new DeleteOrderByIdCommandImpl());

    Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
