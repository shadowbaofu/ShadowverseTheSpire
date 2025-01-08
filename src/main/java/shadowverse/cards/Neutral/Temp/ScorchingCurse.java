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
        if (p.hand.group.stream().anyMatch(abstractCard -> abstractCard.type == CardType.ATTACK)){
            addToBot(new SelectCardsInHandAction(1, TEXT[0], false, true, card -> card.type == CardType.ATTACK, abstractCards -> {
                if (abstractCards.size()==0){
                    addToBot(new DamageAllEnemiesAction(p, this.damage, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
                    addToBot(new AddTemporaryHPAction(p, p, 3));
                }
                for (AbstractCard c : abstractCards) {{
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractCard tempCard = c.makeSameInstanceOf();
                            if (tempCard.costForTurn > 0) {
                                tempCard.costForTurn = 0;
                                tempCard.isCostModifiedForTurn = true;
                            }
                            tempCard.baseDamage = 0;
                            tempCard.baseBlock = 0;
                            tempCard.exhaustOnUseOnce = true;
                            tempCard.exhaust = true;
                            tempCard.isEthereal = true;
                            tempCard.initializeDescription();
                            tempCard.applyPowers();
                            tempCard.lighten(true);
                            tempCard.setAngle(0.0F);
                            tempCard.drawScale = 0.12F;
                            tempCard.targetDrawScale = 0.75F;
                            tempCard.current_x = Settings.WIDTH / 2.0F;
                            tempCard.current_y = Settings.HEIGHT / 2.0F;
                            p.hand.addToTop(tempCard);
                            p.hand.refreshHandLayout();
                            p.hand.applyPowers();
                            this.isDone = true;
                        }
                    });

                }
                }
            }));
        }else {
            addToBot(new DamageAllEnemiesAction(p, this.damage, DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            addToBot(new AddTemporaryHPAction(p, p, 3));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ScorchingCurse();
    }
}
