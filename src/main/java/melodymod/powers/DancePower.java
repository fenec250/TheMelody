package melodymod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import melodymod.cards.AbstractMelodyCard;
import melodymod.patches.MelodyTags;

public class DancePower extends AbstractPower {
    public static final String POWER_ID = "melodymod:DancePower";
    public static final PowerStrings cardStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = cardStrings.NAME;
    public static final String[] DESCRIPTIONS = cardStrings.DESCRIPTIONS;

    public DancePower(AbstractCreature owner, int initialAmount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.region128 = new TextureAtlas.AtlasRegion(new Texture("mysticmod/images/powers/ebb power 84.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(new Texture("mysticmod/images/powers/ebb power 32.png"), 0, 0, 32, 32);
        this.type = PowerType.BUFF;
        this.amount = initialAmount;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
    	this.description = DESCRIPTIONS[0];
    }

//    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;

        manageSteps(stackAmount);

        this.amount = stackAmount;
        if (this.amount <= 0) {
	        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
        updateHandCosts();
    }

	@Override
	public void onInitialApplication() {
		super.onInitialApplication();
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
				new StepPower(this.owner, 1)));
		updateHandCosts();
	}

	@Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // if we stop dancing, stop dancing.
        // if we continue Dancing the DanceAction should manage this power's amount.
        if (card.tags.stream().noneMatch(MelodyTags.IS_DANCE::equals)) {
	        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
			        this.owner, this.owner, this));
        }
    }

	@Override
	public void onDrawOrDiscard() {
		super.onDrawOrDiscard();
		updateHandCosts();
	}

	@Override
	public void reducePower(int reduceAmount) {
		super.reducePower(reduceAmount);
	}

	@Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
	        this.owner, this.owner, this));
    }

	private void manageSteps(int stackAmount) {
		if (AbstractDungeon.player.hasPower(StepPower.POWER_ID)) {
			StepPower steps = (StepPower) AbstractDungeon.player.getPower(StepPower.POWER_ID);
			if (this.amount <= stackAmount) { // if we change dance, reset Step count
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
						this.owner, this.owner, steps));
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
						new StepPower(this.owner, 1)));
			}
			else if (stackAmount > 0) { // else if still dancing increment Step count
				AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
						new StepPower(this.owner, 1), 1));
			}
			else { // else remove Steps as we stop dancing
				AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
						this.owner, this.owner, steps));
			}
		}
		else if (stackAmount > 0) { // else if still dancing increment Step count
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner,
					new StepPower(this.owner, 1), 1));
		}
	}

	private void updateHandCosts() {
		AbstractDungeon.player.hand.group.stream()
				.filter(c -> c.hasTag(MelodyTags.IS_DANCE))
				.map(c -> (AbstractMelodyCard) c)
				.forEach(this::updateDanceCost);
	}

	private void updateDanceCost(AbstractMelodyCard card) {
    	if (card.dance < this.amount)
		    card.setCostForTurn(0, true);
	    else
		    card.setCostForTurn(card.preDanceCost, true);
	}
}
