package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import shadowverse.action.DarkAngelAction;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Vampire;

public class AngelChoice extends CustomCard {
    public static final String ID = "shadowverse:AngelChoice";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:AngelChoice");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/WingedInversion.png";

    public AngelChoice() {
        super(ID, NAME, IMG_PATH, -2, DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.NONE);
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            initializeDescription();
        }
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    public void onChoseThisOption() {
        addToBot(new SelectCardsInHandAction(1, TEXT[0], false, true, card -> card.type == CardType.ATTACK
                || card instanceof DarkGabriel, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                if (c.hasTag(AbstractShadowversePlayer.Enums.DARK_ANGEL)){
                    if (c.cardsToPreview != null && c.type == CardType.ATTACK){
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                                AbstractDungeon.player.hand.removeCard(c);
                                this.isDone = true;
                            }
                        });
                        addToBot(new MakeTempCardInHandAction(c.cardsToPreview.makeStatEquivalentCopy()));
                    }
                    addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,2)));
                    addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,2)));
                }else {
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
                            AbstractDungeon.player.hand.removeCard(c);
                            this.isDone = true;
                        }
                    });
                    AbstractCard f = new FallenAngel();
                    if (this.upgraded)
                        f.upgrade();
                    addToBot(new MakeTempCardInHandAction(f));
                }
            }
        }));
    }

    public AbstractCard makeCopy() {
        return new AngelChoice();
    }
}
