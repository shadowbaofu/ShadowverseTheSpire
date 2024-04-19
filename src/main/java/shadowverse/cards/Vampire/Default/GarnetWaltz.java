package shadowverse.cards.Vampire.Default;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.GetEPAction;
import shadowverse.cards.Neutral.Temp.MonoResolve;
import shadowverse.cards.Neutral.Temp.UriasRevelry;
import shadowverse.characters.Vampire;
import shadowverse.powers.EpitaphPower;
import shadowverse.powers.WrathPower;
import shadowverse.stance.Vengeance;

import java.util.ArrayList;
import java.util.List;


public class GarnetWaltz
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:GarnetWaltz";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:GarnetWaltz");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:GarnetWaltz2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/GarnetWaltz.png";

    public GarnetWaltz() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Vampire.Enums.COLOR_SCARLET, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.cardsToPreview = new UriasRevelry();
        this.baseMagicNumber = 12;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
                stanceChoices.add(new UriasRevelry());
                stanceChoices.add(new MonoResolve());
                if (abstractPlayer.hasPower(EpitaphPower.POWER_ID) || abstractPlayer.stance.ID.equals(Vengeance.STANCE_ID)) {
                    addToBot(new SFXAction("GarnetWaltz"));
                    addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, this.magicNumber, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    addToBot(new GetEPAction(true, 1));
                } else {
                    addToBot(new ChooseOneAction(stanceChoices));
                }
                break;
            case 1:
                addToBot(new SFXAction("GarnetWaltz2"));
                addToBot(new SFXAction("ATTACK_HEAVY"));
                addToBot(new VFXAction(abstractPlayer, new CleaveEffect(), 0.1F));
                addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.NONE));
                addToBot(new DrawCardAction(2));
                if (abstractPlayer.stance.ID.equals(Vengeance.STANCE_ID) || abstractPlayer.hasPower(WrathPower.POWER_ID) || abstractPlayer.hasPower(EpitaphPower.POWER_ID)) {
                    addToBot(new DamageAllEnemiesAction(abstractPlayer, DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    addToBot(new HealAction(abstractPlayer, abstractPlayer, 4));
                    addToBot(new GainEnergyAction(1));
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new GarnetWaltz();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++GarnetWaltz.this.timesUpgraded;
                GarnetWaltz.this.upgraded = true;
                GarnetWaltz.this.name = cardStrings.NAME + "+";
                GarnetWaltz.this.initializeTitle();
                GarnetWaltz.this.upgradeBaseCost(0);
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++GarnetWaltz.this.timesUpgraded;
                GarnetWaltz.this.upgraded = true;
                GarnetWaltz.this.name = cardStrings2.NAME;
                GarnetWaltz.this.initializeTitle();
                GarnetWaltz.this.rawDescription = cardStrings2.DESCRIPTION;
                GarnetWaltz.this.initializeDescription();
                GarnetWaltz.this.upgradeBaseCost(2);
                GarnetWaltz.this.baseDamage = 16;
                GarnetWaltz.this.upgradedDamage = true;
                GarnetWaltz.this.cardsToPreview = null;
            }
        });
        return list;
    }
}

