package com.bundledev.amqp.receiver;

import com.bundledev.model.BundleDTO;

public interface IReceiver {

	public void receive(BundleDTO item);
}
