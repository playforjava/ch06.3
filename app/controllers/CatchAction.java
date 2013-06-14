package controllers;

import play.mvc.*;
import utils.ExceptionMailer;

public class CatchAction extends Action.Simple {
  public Result call(Http.Context ctx) {
    try {
      return delegate.call(ctx);
    } catch (Throwable e) {
      ExceptionMailer.send(e);
      throw new RuntimeException(e);
    }
  }
}
