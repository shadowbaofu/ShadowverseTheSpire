package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.GrandFinalEffect;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Nemesis;
import shadowverse.powers.PurelightExemplarPower;

public class PurelightExemplar
        extends CustomCard {
    public static final String ID = "shadowverse:PurelightExemplar";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:PurelightExemplar");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/PurelightExemplar.png";

    public PurelightExemplar() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.POWER, Nemesis.Enums.COLOR_SKY, CardRarity.SPECIAL, CardTarget.SELF);
        this.tags.add(AbstractShadowversePlayer.Enums.ARTIFACT);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }


    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("PurelightExemplar"));
        addToBot(new VFXAction(new GrandFinalEffect()));
        addToBot( new ApplyPowerAction(p, p, new PurelightExemplarPower(p)));
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new PurelightExemplar();
    }
}


