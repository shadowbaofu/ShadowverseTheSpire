package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import shadowverse.action.DarkAngelAction;
import shadowverse.cards.Neutral.Neutral.WingedInversion;
import shadowverse.characters.AbstractShadowversePlayer;

public class SeraphOfSin extends CustomCard {
    public static final String ID = "shadowverse:SeraphOfSin";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/SeraphOfSin.png";
    private static final String TEXT = CardCrawlGame.languagePack.getUIString("shadowverse:Exhaust").TEXT[0];


    public SeraphOfSin() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.cardsToPreview = new SeraphOfVirtue();
        this.tags.add(AbstractShadowversePlayer.Enums.DARK_ANGEL);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(3);
            this.cardsToPreview.upgrade();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard tmp = cardsToPreview.makeStatEquivalentCopy();
                tmp.exhaustOnUseOnce = true;
                tmp.exhaust = true;
                tmp.rawDescription += " NL " + TEXT + " 。";
                tmp.initializeDescription();
                tmp.applyPowers();
                tmp.lighten(true);
                tmp.setAngle(0.0F);
                tmp.drawScale = 0.12F;
                tmp.targetDrawScale = 0.75F;
                tmp.current_x = Settings.WIDTH / 2.0F;
                tmp.current_y = Settings.HEIGHT / 2.0F;
                abstractPlayer.hand.addToTop(tmp);
                abstractPlayer.hand.refreshHandLayout();
                abstractPlayer.hand.applyPowers();
                this.isDone = true;
            }
        });
        if (upgraded){
            addToBot(new MakeTempCardInHandAction(new FallenAngel()));
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new SeraphOfSin();
    }
}


