package com.creatix.mc.thebot.bot.neuralsystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.creatix.mc.thebot.bot.TheBot;
import com.googlecode.fannj.ActivationFunction;
import com.googlecode.fannj.Fann;
import com.googlecode.fannj.Layer;
import com.googlecode.fannj.Trainer;
import com.googlecode.fannj.TrainingAlgorithm;

public class NeuralSystem {
	
	public static void init(TheBot bot) {
		bot.sendChatMessage("Starting neural core...");/**
		List<Layer> layers = new ArrayList<>();
		layers.add(Layer.create(3, ActivationFunction.FANN_SIGMOID_SYMMETRIC, 0.01f));
		layers.add(Layer.create(16, ActivationFunction.FANN_SIGMOID_SYMMETRIC, 0.01f));
		layers.add(Layer.create(4, ActivationFunction.FANN_SIGMOID_SYMMETRIC, 0.01f));
		Fann f = new Fann(layers);
		Trainer t = new Trainer(f);
		t.setTrainingAlgorithm(TrainingAlgorithm.FANN_TRAIN_RPROP);
		t.train(new File("D:/t.txt").getAbsolutePath(), 100000, 100, 0.0001f);
		f.save("ann");
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test() {
		 Fann fann = new Fann("ann");
	        float[][] tests = {
	                {1.0f, 0, 1},
	                {0.9f, 1, 3},
	                {0.3f, 0, 8},
	                {1, 1, 8},
	                {0.1f, 0, 0},
	        };
	        for (float[] test:tests){
	            System.out.println(getAction(fann.run(test)));
	        }
	    }
	    
	    private static String getAction(float[] out){
	        int i = 0;
	        for (int j = 1; j < 4; j++) {
	            if(out[i]<out[j]){
	                i = j;
	            }
	        }
	        switch (i){
	            case 0:return "атаковать";
	            case 1:return "прятаться";
	            case 2:return "бежать";
	            case 3:return "ничего не делать";
	        }
	        return "";**/
	}
	
}
