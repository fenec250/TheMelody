package melodymod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import melodymod.powers.DancePower;
import melodymod.powers.StepPower;

public class DrawForEachStepAction extends AbstractGameAction {

	public DrawForEachStepAction(AbstractCreature source) {
		this.target = source;
		this.source = source;
		this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
		this.actionType = ActionType.CARD_MANIPULATION;
	}

	public void update() {
		if (this.target != null && this.target.hasPower(DancePower.POWER_ID)) {
			int draw = ((DancePower) this.target.getPower(DancePower.POWER_ID)).amount2;
			AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.source, draw));
		}
		this.isDone = true;

		this.tickDuration();
	}
}
