package shadowverse.cards.Royal.Loot;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;

public class UltimateHollow extends CustomCard {
    public static final String ID = "shadowverse:UltimateHollow";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:UltimateHollow");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/UltimateHollow.png";

    public UltimateHollow() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Royal.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = 5;
        this.baseBlock = 5;
        this.tags.add(AbstractShadowversePlayer.Enums.GILDED);
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(3);
            upgradeBlock(3);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("UltimateHollow"));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new GainBlockAction(abstractPlayer, this.block));
    }

    @Override
    public void triggerWhenDrawn() {
        addToBot(new SFXAction(ID.replace("shadowverse:", "") + "_Eff"));
        AbstractCard c=new UltimateHollow();
        if(this.upgraded){
            c.upgrade();
        }
        this.addToTop(new MakeTempCardInDiscardAction(c, 1));
    }


    @Override
    public AbstractCard makeCopy() {
        return new UltimateHollow();
    }
}

