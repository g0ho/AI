
import java.util.Random;

import commandcenter.CommandCenter;
import enumerate.Action;
import enumerate.State;
import structs.FrameData;
import structs.GameData;
import structs.Key;
import gameInterface.AIInterface;


public class JenJaAi implements AIInterface {

	Key inputKey;
	boolean playerNumber;
	FrameData frameData;
	CommandCenter cc;
	GameData gameData;
	Action[] actionAir;
	Action[] actionGround;
	
	 @Override
	 public int initialize(GameData gameData, boolean playerNumber)
	 
	 {
		 this.gameData = gameData;
		 this.playerNumber = playerNumber;
		 this.inputKey = new Key();
		 cc = new CommandCenter();
		 frameData = new FrameData();
		 this.actionAir =
			        new Action[] {Action.AIR_A, Action.AIR_B, Action.AIR_DA, Action.AIR_DB,
			            Action.AIR_FA, Action.AIR_FB, Action.AIR_UA, Action.AIR_UB, Action.AIR_D_DF_FA,
			            Action.AIR_D_DF_FB, Action.AIR_F_D_DFA, Action.AIR_F_D_DFB, Action.AIR_D_DB_BA,
			            Action.AIR_D_DB_BB};
		 this.actionGround =
			        new Action[] {Action.STAND_D_DB_BA,Action.STAND_A, Action.STAND_B,
			            Action.CROUCH_A, Action.CROUCH_B, Action.STAND_FA, Action.STAND_FB, Action.CROUCH_FA,
			            Action.CROUCH_FB,Action.STAND_F_D_DFA,
			            Action.STAND_F_D_DFB, Action.STAND_D_DB_BB};
		 return 0;
	 }
	
	 
	 private Action getGAction(){
		 Action jenja = null;
		 int rnd = new Random().nextInt(this.actionGround.length);
		 jenja = this.actionGround[rnd];
		 return jenja;
	 }
	 
	 private Action getAAction(){
		 Action jenja = null;
		 int rnd = new Random().nextInt(this.actionAir.length);
		 jenja = this.actionAir[rnd];
		 return jenja;
	 }
	
	 @Override
	 public void getInformation(FrameData frameData)
	 {
		 this.frameData = frameData;
		 cc.setFrameData(this.frameData, playerNumber);
	 }
	
	 @Override
	 public void processing() {
		 if(!frameData.getEmptyFlag() && frameData.getRemainingTime() > 0) {
			 if(cc.getSkillFlag()) {
				 inputKey = cc.getSkillKey();
			 } else {
				 inputKey.empty();
				 cc.skillCancel();
			 if ((cc.getEnemyEnergy() >= 300) && ((cc.getMyHP() - cc.getEnemyHP()) <= 300)){
						cc.commandCall("FOR_JUMP _B B B");
			 }
			 else if (!cc.getMyCharacter().getState().equals(State.AIR) && !cc.getMyCharacter().getState().equals(State.DOWN)) {
				 if(cc.getEnemyCharacter().getState().equals(State.AIR) && cc.getMyEnergy() >= 300){
					 cc.commandCall("STAND_D_DF_FC"); 
				 }
//					if ((cc.getDistanceX() > 150) && (cc.getMyEnergy() >= 50)) {
//						cc.commandCall("FOR_JUMP _B B B");
//					}
//					else if ((cc.getDistanceX() > 150))
//						cc.commandCall("FOR_JUMP");
//					else if (cc.getMyEnergy() >= 300)
//						cc.commandCall("STAND_D_DF_FC"); 
//					else if ((cc.getDistanceX() > 100) && (cc.getMyEnergy() >= 50))
//						cc.commandCall("STAND_D_DB_BB"); 
//					else if ((cc.getDistanceX() > 100) && cc.getEnemyCharacter().getState().equals(State.AIR))
//						cc.commandCall("STAND_F_D_DFA"); 
					else if (cc.getDistanceX() > 100)
							cc.commandCall("6 6 6");
					else if (cc.getDistanceX() < 50)
						    cc.commandCall("CROUCH_A CROUCH_A CROUCH_A");
					else
							cc.commandCall("STAND_B STAND_A STAND_A STAND_F_D_DFA");
			 }
			 else if ((cc.getDistanceX() <= 150) && (cc.getMyCharacter().getState().equals(State.AIR) || cc.getMyCharacter().getState().equals(State.DOWN))
					&& (((gameData.getStageXMax() - cc.getMyCharacter().getX())>=200) || (cc.getMyCharacter().getX() - cc.getEnemyX()> 0))
					&& ((cc.getMyCharacter().getX() >=200) || cc.getMyCharacter().getX() - cc.getEnemyX() < 0)) { 
				if (cc.getMyEnergy() >= 5)
					cc.commandCall("FOR_JUMP AIR_DB");
//				else
//					cc.commandCall(this.getAAction().name());
			}
			else{
//				cc.commandCall(this.getGAction().name());
				cc.commandCall("FOR_JUMP");
			}
 }
	 }
	 }
	
	 @Override
	 public Key input()
	 {
		 return inputKey;
	 }
	
	 @Override
	 public void close(){
	
	 }
	
	 @Override
	 public String getCharacter() {
		 return CHARACTER_LUD;
	 }
	
	 } 
	