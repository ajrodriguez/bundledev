package com.bundledev.amqp.sender;

import com.bundledev.model.BundleDTO;

public interface ISender {

	public void send(BundleDTO item);
}
