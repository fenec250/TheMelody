package melodymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;
import melodymod.powers.RhythmPower;

import java.util.Iterator;

public class Shout
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:Shout";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "melodymod/images/cards/strike.png";
    private static final int COST = 1;
    private static final int DAMAGE_AMT = 4;
    private static final int UPGRADE_DAMAGE_AMT = 2;
    private static final int WEAK = 2;
    private static final int UPGRADE_WEAK = 1;
    private static final int RHYTHM = 2;
    private static final int TEMPO = 3;

    boolean tempoActive;

    public Shout() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.MELODY_LIME,
                CardRarity.COMMON, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.isMultiDamage = true;
        this.magicNumber = this.baseMagicNumber = WEAK;
        this.tempoActive = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.tempo(p);
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(
                p, this.multiDamage, this.damageTypeForTurn,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shout();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE_AMT);
            this.upgradeMagicNumber(UPGRADE_WEAK);
        }
    }

    private void tempo(AbstractPlayer p) {
        // try to consume Tempo
        if (!tempoActive && p.hasPower(RhythmPower.POWER_ID) && p.getPower(RhythmPower.POWER_ID).amount >= TEMPO) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, RhythmPower.POWER_ID, TEMPO));
            tempoActive = true;
            this.rawDescription = EXTENDED_DESCRIPTION[0];
            this.initializeDescription();

            this.exhaust = true;
            this.tags.add(MelodyTags.IS_RHYTHM);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RhythmPower(p, RHYTHM), RHYTHM));

            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

            while (var3.hasNext()) {
                AbstractMonster mo = (AbstractMonster) var3.next();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
}