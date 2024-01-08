package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.characters.Witchcraft;

public class ScorchingCurse extends CustomCard {
    public static final String ID = "shadowverse:ScorchingCurse";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:ScorchingCurse");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/ScorchingCurse.png";
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString("DualWieldAction").TEXT;

    public ScorchingCurse() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Witchcraft.Enums.COLOR_BLUE, CardRarity.SPECIAL, CardTarget.NONE);
        this.baseDamage = 9;
        this.isEthereal = true;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeDamage(3);
            this.isEthereal = false;
            this.selfRetain = true;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("ScorchingCurse"));
        addToBot(new SelectCardsInHandAction(1, TEXT[0],  false,true, card -> card.type == CardType.ATTACK, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                if (c == null){
                    addToBot(new DamageAllEnemiesAction(p, this.damage, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                    addToBot(new AddTemporaryHPAction(p,p,3));
                }else {
                    c.baseDamage = 0;
                    c.baseBlock = 0;
                    c.exhaustOnUseOnce = true;
                    c.exhaust = true;
                    c.isEthereal = true;
                    c.initializeDescription();
                    c.applyPowers();
                    c.lighten(true);
                    c.setAngle(0.0F);
                    c.drawScale = 0.12F;
                    c.targetDrawScale = 0.75F;
                    c.current_x = Settings.WIDTH / 2.0F;
                    c.current_y = Settings.HEIGHT / 2.0F;
                    p.hand.addToTop(c);
                }
            }
        }));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ScorchingCurse();
    }
}
