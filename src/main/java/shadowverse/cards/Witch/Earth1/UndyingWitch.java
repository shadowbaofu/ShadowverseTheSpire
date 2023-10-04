package shadowverse.cards.Witch.Earth1;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Witchcraft;
import shadowverse.powers.EarthEssence;
import shadowverse.powers.UndyingWitchPower;

public class UndyingWitch extends CustomCard {
    public static final String ID = "shadowverse:UndyingWitch";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:UndyingWitch");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/UndyingWitch.png";

    public UndyingWitch() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, CardType.ATTACK, Witchcraft.Enums.COLOR_BLUE, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 6;
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    public void applyPowers() {
        super.applyPowers();
        int count = 0;
        if (AbstractDungeon.player instanceof AbstractShadowversePlayer) {
            count = ((AbstractShadowversePlayer) AbstractDungeon.player).earthCount;
        }
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + count;
        this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SFXAction("UndyingWitch"));
        if (abstractPlayer instanceof AbstractShadowversePlayer) {
            int count = ((AbstractShadowversePlayer) abstractPlayer).earthCount;
            if (count >= 7) {
                addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
                addToBot(new MakeTempCardInHandAction(new Miracle()));
                if (!abstractPlayer.hasPower(UndyingWitchPower.POWER_ID)) {
                    addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new UndyingWitchPower(abstractPlayer)));
                }
            }
        }
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new EarthEssence(abstractPlayer,1),1));
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractMonster, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (this.upgraded) {
            addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new UndyingWitchPower(abstractPlayer)));
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new UndyingWitch();
    }
}

