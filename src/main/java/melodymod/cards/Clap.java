package melodymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import melodymod.patches.AbstractCardEnum;
import melodymod.patches.MelodyTags;
import melodymod.powers.RhythmPower;

public class Clap
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:Clap";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "mysticmod/images/cards/strike.png";
    private static final int COST = 0;
    private static final int DAMAGE_AMT = 3;
    private static final int UPGRADE_DAMAGE_AMT = 1;
    private static final int RHYTHM = 1;
    private static final int UPGRADE_DRAW_AMT = 1;

    public Clap() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.MELODY_LIME,
                CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.magicNumber = this.baseMagicNumber = RHYTHM;
        this.tags.add(MelodyTags.IS_RYTHME);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.step(p);

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RhythmPower(p, RHYTHM), this.magicNumber, true));

        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, UPGRADE_DRAW_AMT));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Clap();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DAMAGE_AMT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}