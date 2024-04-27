package shadowverse.cards.Neutral.Temp;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import shadowverse.cards.Royal.NatMech.Bayleon;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Royal;
import shadowverse.orbs.AmbushMinion;
import shadowverse.orbs.ErikaOrb;
import shadowverse.orbs.Minion;

public class LuminousBlade extends CustomCard {
    public static final String ID = "shadowverse:LuminousBlade";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/LuminousBlade.png";

    public LuminousBlade() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.SKILL, Royal.Enums.COLOR_YELLOW, CardRarity.SPECIAL, CardTarget.SELF);
        this.tags.add(AbstractShadowversePlayer.Enums.NATURAL);
        this.baseDamage = 6;
        this.exhaust = true;
    }


    @Override
    public void upgrade() {
        upgradeName();
        upgradeDamage(4);
    }


    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction(ID.replace("shadowverse:", "")));
        addToBot(new SelectCardsAction(abstractPlayer.discardPile.group,1, TEXT[0], true, card -> {
            return card.type == CardType.ATTACK && card.hasTag(AbstractShadowversePlayer.Enums.NATURAL);
        }, abstractCards -> {
            for (AbstractCard c : abstractCards) {
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer,1),1));
                addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DexterityPower(abstractPlayer,1),1));
                if (rally() >= 20){
                    addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer,1),1));
                    addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DexterityPower(abstractPlayer,1),1));
                }
                if (c instanceof Bayleon){
                    addToBot(new DamageRandomEnemyAction(new DamageInfo(abstractPlayer,this.damage,this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                }
            }
        }));
    }

    public int rally() {
        int rally = 0;

        for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
            if (o instanceof Minion && !(o instanceof AmbushMinion) && !(o instanceof ErikaOrb)) {
                rally++;
            }
        }

        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat) {
            if (c.type == CardType.ATTACK && !(c.hasTag(CardTags.STRIKE))) {
                rally++;
            }
            if (c instanceof NaterranGreatTree)
                rally++;
        }
        return rally;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + rally() + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new LuminousBlade();
    }
}

