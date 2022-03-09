package de.tuhh.diss.harborstorage;

import de.tuhh.diss.harborstorage.HarborStorageManagement;
import de.tuhh.diss.harborstorage.sim.*;
import de.tuhh.diss.io.SimpleIO;

public class SimpleHarborStorageTest {

	private static HarborStorageManagement hsm;
	private static int RACK_CAPACITY = 29;
	
	private static boolean testTooLargePacket() {
		
	    boolean refuse = false;
		int width = 10;
		int height = 10;
		int depth = 10;
		int weight = 500;
		String description = "Really Large And Heavy Packet";
		
		SimpleIO.println("Test 1: trying to put too large packet in rack");
		try {
			hsm.storePacket(width, height, depth, description, weight);
		} catch( StorageException e ) {
			SimpleIO.println("Package storage refused, package too large");
			refuse = true;
		}
		
		return refuse;
	}
	
	private static boolean testTooManyPackets() {
	    
		int packetsToStore = 100;
		int storedPackets = 0;
		
		int width = 1;
		int height = 1;
		int depth = 1;
		int weight = 1;
		String description = "Small packet #";
				
		SimpleIO.println("Test 2: trying to put many small packets in rack");
		while( storedPackets <= packetsToStore ) {
			try {
				hsm.storePacket(width, height, depth, (description+storedPackets), weight);
			} catch(StorageException e) {
				SimpleIO.println("Harbor storage full");
				break;
			}
			storedPackets++;
		}
		
		return storedPackets == RACK_CAPACITY;
		
	}
	
	private static void startTestProcedure() {
		
		////////////// Test 1 ////////////		
		if( testTooLargePacket() ) {
			SimpleIO.println("Test successfull, too large packet was refused");
		} else {
			SimpleIO.println("Test failed, too large packet was stored");
		}
		//////////// Test 2 //////////////
		if( testTooManyPackets() ) {
			SimpleIO.println("Test successfull, only stored " + RACK_CAPACITY + " packets.");	
		} else {
			SimpleIO.println("Test failed");
		}
	}
	
	public static void main(String[] args) {
		hsm = new HarborStorageManagement();
		startTestProcedure();
		hsm.shutdown();
	}

}
