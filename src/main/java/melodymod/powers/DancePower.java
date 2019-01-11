package melodymod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melodymod.cards.AbstractMelodyCard;
import melodymod.patches.MelodyTags;

public class DancePower extends TwoAmountPower {
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
        amount2 = 1;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    }

//    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;

        manageSteps(stackAmount);

        this.amount = stackAmount;
        updateHandCosts();
    }

	@Override
	public void onInitialApplication() {
		super.onInitialApplication();
		updateHandCosts();
	}

	@Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        // if we stop dancing, stop dancing.
        if (card.tags.stream().noneMatch(MelodyTags.IS_DANCE::equals)) {
	        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(
			        this.owner, this.owner, this));
	        this.amount = 0;
	        updateHandCosts();
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
		if (this.amount <= stackAmount) { // if we change dance, reset Step count
			this.amount2 = 1;
		}
		else { // else increment Step count
			this.amount2 += 1;
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
