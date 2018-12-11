package melodymod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import melodymod.powers.StepPower;

public abstract class AbstractMelodyCard extends CustomCard {
//    public int baseSecondMagicNumber;
//    public int secondMagicNumber;
//    public boolean isSecondMagicNumberModified;
//    public boolean upgradedSecondMagicNumber;

    public AbstractMelodyCard(final String id, final String name, final String img, final int cost, final String rawDescription,
                              final AbstractCard.CardType type, final AbstractCard.CardColor color,
                              final AbstractCard.CardRarity rarity, final AbstractCard.CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
    }

    protected void step(AbstractPlayer p) {
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p,p,new StepPower(p, 1), 1));
    }

    protected boolean dance(AbstractPlayer p, int step) {
        return (p.getPower(StepPower.POWER_ID).amount == step);
    }
//
//    public void upgradeToArte() {
//        this.tags.add(MysticTags.IS_ARTE);
//    }
//
//    public void upgradeSecondMagicNumber(int amount) {
//        this.baseSecondMagicNumber += amount;
//        this.secondMagicNumber = this.baseSecondMagicNumber;
//        this.upgradedSecondMagicNumber = true;
//    }
//
//    public static class SecondMagicNumber extends DynamicVariable {
//
//        @Override
//        public int baseValue(AbstractCard card) {
//            return ((AbstractMelodyCard)card).baseSecondMagicNumber;
//        }
//
//        @Override
//        public boolean isModified(AbstractCard card) {
//            return ((AbstractMelodyCard)card).isSecondMagicNumberModified;
//        }
//
//        @Override
//        public String key() {
//            return "mysticmod:M2";
//        }
//
//        @Override
//        public boolean upgraded(AbstractCard card) {
//            return ((AbstractMelodyCard)card).upgradedSecondMagicNumber;
//        }
//
//        @Override
//        public int value(AbstractCard card) {
//            return ((AbstractMelodyCard)card).secondMagicNumber;
//        }
//    }
}