package melodymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;
import melodymod.powers.DancePower;
import melodymod.powers.RhythmPower;

import java.util.Iterator;

public class Whip
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:Whip";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "melodymod/images/cards/strike.png";
    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    private static final int DANCE = 2;
    private static final int WEAK = 2;
    private static final int TEMPO = 2;

    boolean tempoActive;

    public Whip() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.MELODY_LIME,
                CardRarity.RARE, CardTarget.ENEMY);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = WEAK;
        this.dance = DANCE;
        this.tags.add(MelodyTags.IS_DANCE);
        this.tags.add(MelodyTags.IS_TEMPO);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.dance(p);
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
        this.tempo(p, m);
    }

    @Override
    public AbstractCard makeCopy() {
        return new Whip();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DAMAGE);
        }
    }

    private void tempo(AbstractPlayer p, AbstractMonster m) {
        // try to consume Tempo
        if (!tempoActive && p.hasPower(RhythmPower.POWER_ID) && p.getPower(RhythmPower.POWER_ID).amount >= TEMPO) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, RhythmPower.POWER_ID, TEMPO));
            tempoActive = true;
            this.rawDescription = EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }
        if (tempoActive)
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }
}