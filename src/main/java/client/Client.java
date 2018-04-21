package client;

import java.io.Serializable;

import constants.MyOption;

public interface Client extends Serializable {

	public void init(MyOption runtimeOption);
	public void post(String message);
}
