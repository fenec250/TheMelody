package melodymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;
import melodymod.powers.RhythmPower;

public class Spin
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:Spin";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "melodymod/images/cards/strike.png";
    private static final int COST = 1;
    private static final int DAMAGE_AMT = 7;
    private static final int UPGRADE_DAMAGE_AMT = 3;
    private static final int TEMPO = 2;

    boolean isEcho;

    public Spin() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.MELODY_LIME,
                CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.isMultiDamage = true;
        this.tags.add(MelodyTags.IS_TEMPO);

        this.tempo = TEMPO;
        this.isEcho = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
                p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (this.tempo(p)) {
            Spin card = (Spin) this.makeStatEquivalentCopy();
            if (!this.isEcho)
                card.name = "Echo: " + card.name;
            card.exhaust = true;
            card.isEthereal = true;
            card.costForTurn = 1;
            card.isEcho = true;
            card.rawDescription = EXTENDED_DESCRIPTION[0] + EXTENDED_DESCRIPTION[1];
            initializeDescription();
//            card.update();
//            Cleaving Finish in Hubris: LimboToHandAction would be perfect.
//            Instead I am copying the Glutton Echo card and adding exhaust on discard.
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
        }
    }

    @Override
    public void onMoveToDiscard() {
        if (this.isEcho)
            AbstractDungeon.actionManager.addToBottom(
                    new ExhaustSpecificCardAction(this, AbstractDungeon.player.discardPile));
    }

//    private AbstractCard echoCard(){
//        AbstractCard card = this.c.makeStatEquivalentCopy();
//        card.name = "Echo: " + card.name;
//        card.exhaust = true;
//        card.isEthereal = true;
//        AlwaysRetainField.alwaysRetain.set(card, false);
//        card.retain = false;
//        if(card.cost >= 0 && this.discount>0)
//            card.updateCost(-1*this.discount);
//        return card;
//    }

    @Override
    public AbstractCard makeCopy() {
        Spin copy = new Spin();
        copy.isEcho = this.isEcho;
        copy.rawDescription = this.rawDescription;
        copy.initializeDescription();
        return copy;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE_AMT);
        }
    }
}