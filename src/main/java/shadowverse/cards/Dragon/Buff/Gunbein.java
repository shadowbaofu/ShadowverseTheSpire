package shadowverse.cards.Dragon.Buff;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;


public class Gunbein
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:Gunbein";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Gunbein");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Gunbein.png";
    private boolean trigger;

    public Gunbein() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.ATTACK, Dragon.Enums.COLOR_BROWN, CardRarity.COMMON, CardTarget.SELF, 0, CardType.SKILL);
        this.baseBlock = 10;
        this.tags.add(AbstractShadowversePlayer.Enums.ACADEMIC);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    @Override
    public void baseUse(AbstractPlayer abstractPlayer, AbstractMonster m) {
        addToBot(new SFXAction("Gunbein"));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
        if (abstractPlayer.hasPower(DexterityPower.POWER_ID) && abstractPlayer.getPower(DexterityPower.POWER_ID).amount >= 2) {
            addToBot(new GainBlockAction(abstractPlayer, this.block));
        }
    }

    @Override
    public void accUse(AbstractPlayer abstractPlayer, AbstractMonster m) {
        addToBot(new SFXAction("Gunbein_Acc"));
        if (!trigger) {
            trigger = true;
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DexterityPower(abstractPlayer, 1), 1));
        }
        addToBot(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer, 4, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new Gunbein();
    }
}

