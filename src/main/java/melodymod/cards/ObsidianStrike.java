package melodymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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

public class ObsidianStrike
        extends AbstractMelodyCard {
    public static final String ID = "melodymod:ObsidianStrike";
    public static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    public static final String IMG_PATH = "melodymod/images/cards/strike.png";
    private static final int COST = 1;
    private static final int DAMAGE_AMT = 5;
    private static final int DAMAGE_SCALING = 2;
    private static final int UPGRADE_DAMAGE_SCALING = 1;

    public ObsidianStrike() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION,
                CardType.ATTACK, AbstractCardEnum.MELODY_LIME,
                CardRarity.BASIC, CardTarget.ENEMY);
        this.damage = this.baseDamage = DAMAGE_AMT;
        this.magicNumber = this.baseMagicNumber = DAMAGE_SCALING;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // try to increase damage
        if (p.hasPower(RhythmPower.POWER_ID)) {
            int damageIncrease = Math.max(p.getPower(RhythmPower.POWER_ID).amount * this.magicNumber, 0);
            this.baseDamage += damageIncrease;
            this.damage += damageIncrease;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer p, AbstractMonster mo, float tmp) {
        if (p.hasPower(RhythmPower.POWER_ID)) {
            tmp += Math.min(p.getPower(RhythmPower.POWER_ID).amount * this.magicNumber, 0);
            // TODO: the bonus damage is not affected by weak. It should deal reduced damage but stack at the same rate.
        }
        return tmp;
    }

    @Override
    public void applyPowers() {
        if (AbstractDungeon.player.hasPower(RhythmPower.POWER_ID)) {
            this.damage = baseDamage + Math.max(AbstractDungeon.player.getPower(RhythmPower.POWER_ID).amount * this.magicNumber, 0);
        }
        super.applyPowers();
        this.initializeDescription();
    }


    @Override
    public AbstractCard makeCopy() {
        ObsidianStrike obsidianStrike = new ObsidianStrike();
        obsidianStrike.upgradeDamage(this.damage - this.baseDamage);
        return obsidianStrike;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_DAMAGE_SCALING);
        }
    }
}