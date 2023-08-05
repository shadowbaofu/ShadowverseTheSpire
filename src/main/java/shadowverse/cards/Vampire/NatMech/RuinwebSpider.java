package shadowverse.cards.Vampire.NatMech;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.EntangleEffect;
import shadowverse.cards.Witch.AbstractAccelerateCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Vampire;
import shadowverse.powers.RuinwebSpiderPower;


public class RuinwebSpider
        extends AbstractAccelerateCard {
    public static final String ID = "shadowverse:RuinwebSpider";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:RuinwebSpider");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/RuinwebSpider.png";

    public RuinwebSpider() {
        super(ID, NAME, IMG_PATH, 5, DESCRIPTION, CardType.ATTACK, Vampire.Enums.COLOR_SCARLET, CardRarity.RARE, CardTarget.ALL_ENEMY, 0, CardType.POWER);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(AbstractShadowversePlayer.Enums.NATURAL);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
            this.isInnate = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void baseUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SkipEnemiesTurnAction());
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            addToBot(new VFXAction(new EntangleEffect(p.hb.cX - 70.0F * Settings.scale, p.hb.cY + 10.0F * Settings.scale, mo.hb.cX, mo.hb.cY), 0.5F));
        }
    }

    @Override
    public void accUse(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new RuinwebSpiderPower(p, this.magicNumber), this.magicNumber));
    }


    public AbstractCard makeCopy() {
        return new RuinwebSpider();
    }
}



