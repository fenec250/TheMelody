package melodymod.cards;

import basemod.abstracts.CustomCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import melodymod.powers.DancePower;
import melodymod.powers.RhythmPower;

public abstract class AbstractMelodyCard extends CustomCard {
    public int dance = -1;
    public int preDanceCost;

    public int tempo = 0;
    public int tempoBase = 0;
	public boolean tempoModified = false;
	public boolean tempoUpgraded = false;
//    public boolean isDancing;
//    public int baseSecondMagicNumber;
//    public int secondMagicNumber;
//    public boolean isSecondMagicNumberModified;
//    public boolean upgradedSecondMagicNumber;

    public AbstractMelodyCard(final String id, final String name, final String img, final int cost, final String rawDescription,
                              final AbstractCard.CardType type, final AbstractCard.CardColor color,
                              final AbstractCard.CardRarity rarity, final AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        this.preDanceCost = this.cost;
    }

    protected boolean tempo(AbstractPlayer p) {
		return this.tempo(p, this.tempo);
    }

	protected boolean tempo(AbstractPlayer p, int amount) {
		if (p.hasPower(RhythmPower.POWER_ID) && p.getPower(RhythmPower.POWER_ID).amount >= amount) {
			return true;
		}
		return false;
	}

    protected void dance(AbstractPlayer p) {
		this.dance(p, this.dance);
    }

	protected void dance(AbstractPlayer p, int dance) {
	    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DancePower(p, dance), dance));
    }

	@Override
	public void setCostForTurn(int amt) {
		this.setCostForTurn(amt, false);
	}

	public void setCostForTurn(int amt, boolean isDancing) {
		this.preDanceCost = this.cost;
		super.setCostForTurn(amt);
		if (!isDancing)
			this.preDanceCost = this.costForTurn;
	}

//    public void upgradeSecondMagicNumber(int amount) {
//        this.baseSecondMagicNumber += amount;
//        this.secondMagicNumber = this.baseSecondMagicNumber;
//        this.upgradedSecondMagicNumber = true;
//
//        this.tags.add(MysticTags.IS_ARTE);
//    }
//
    public static class TempoNumber extends DynamicVariable {

        @Override
        public int baseValue(AbstractCard card) {
            return ((AbstractMelodyCard) card).tempo;
        }

        @Override
        public boolean isModified(AbstractCard card) {
            return ((AbstractMelodyCard) card).tempoModified;
        }

        @Override
        public String key() {
            return "melodyMod:T";
        }

        @Override
        public boolean upgraded(AbstractCard card) {
            return ((AbstractMelodyCard) card).tempoUpgraded;
        }

        @Override
        public int value(AbstractCard card) {
            return ((AbstractMelodyCard) card).tempo;
        }
    }
}