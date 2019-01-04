package melodymod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import melodymod.powers.StepPower;

public class DrawForEachStepAction extends AbstractGameAction {
	private AbstractGameAction action;

	public DrawForEachStepAction(AbstractCreature source, AbstractGameAction action) {
		this.target = source;
		this.source = source;
		this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
		this.actionType = ActionType.CARD_MANIPULATION;
		this.action = action;
	}

	public void update() {
		if (this.target != null && this.target.hasPower(StepPower.POWER_ID)) {
			AbstractDungeon.actionManager.addToTop(new DrawCardAction(this.source, this.target.getPower(StepPower.POWER_ID).amount));
		}

		this.tickDuration();
	}
}
