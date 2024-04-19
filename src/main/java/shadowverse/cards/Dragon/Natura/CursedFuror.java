package shadowverse.cards.Dragon.Natura;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Dragon;
import shadowverse.powers.HeroOfTheHuntPower;
import shadowverse.powers.OverflowPower;

import java.util.ArrayList;
import java.util.List;


public class CursedFuror
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:CursedFuror";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:CursedFuror");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:CursedFuror2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/CursedFuror.png";

    public CursedFuror() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.SKILL, Dragon.Enums.COLOR_BROWN, CardRarity.UNCOMMON, CardTarget.NONE);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void applyPowers() {
        super.applyPowers();
        if (chosenBranch() == 1) {
            if (AbstractDungeon.player instanceof AbstractShadowversePlayer) {
                int count = ((AbstractShadowversePlayer) AbstractDungeon.player).naterranCount;
                this.rawDescription = cardStrings2.DESCRIPTION;
                this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[0] + count;
                this.rawDescription += cardStrings2.EXTENDED_DESCRIPTION[1];
                initializeDescription();
            }
        }
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()) {
            case 0:
                boolean overflow = false;
                if (abstractPlayer.hasPower(OverflowPower.POWER_ID)) {
                    TwoAmountPower p = (TwoAmountPower) abstractPlayer.getPower(OverflowPower.POWER_ID);
                    if (p.amount2 > 0) {
                        overflow = true;
                    }
                }
                if (overflow) {
                    addToBot(new SFXAction("CursedFuror"));
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, this.magicNumber)));
                    addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new OverflowPower(AbstractDungeon.player, 1)));
                    AbstractCreature m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                    if (m != null) {
                        addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.PURPLE, Color.ORANGE), 0.1F));
                        addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, this.magicNumber, false)));
                        addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, this.magicNumber, false)));
                    }
                } else {
                    ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
                    AbstractCard m = new RowenRoar();
                    AbstractCard e = new ValdainClaw();
                    stanceChoices.add(m);
                    stanceChoices.add(e);
                    addToBot(new ChooseOneAction(stanceChoices));
                }
                break;
            case 1:
                addToBot(new SFXAction("CursedFuror2"));
                addToBot(new MakeTempCardInHandAction(new NaterranGreatTree()));
                AbstractCreature m = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
                if (m != null) {
                    addToBot(new VFXAction(new ClawEffect(m.hb.cX, m.hb.cY, Color.PURPLE, Color.ORANGE), 0.1F));
                    addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new VulnerablePower(m, this.magicNumber, false)));
                    addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new WeakPower(m, this.magicNumber, false)));
                }
                if (abstractPlayer instanceof AbstractShadowversePlayer) {
                    if (((AbstractShadowversePlayer) abstractPlayer).naterranCount >= 10) {
                        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new HeroOfTheHuntPower(abstractPlayer, 1), 1));
                    }
                }
                break;
        }
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++CursedFuror.this.timesUpgraded;
                CursedFuror.this.upgraded = true;
                CursedFuror.this.name = cardStrings.NAME + "+";
                CursedFuror.this.initializeTitle();
                CursedFuror.this.cost = 0;
                CursedFuror.this.costForTurn = 0;
                CursedFuror.this.upgradedCost = true;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++CursedFuror.this.timesUpgraded;
                CursedFuror.this.upgraded = true;
                CursedFuror.this.name = cardStrings2.NAME;
                CursedFuror.this.initializeTitle();
                CursedFuror.this.rawDescription = cardStrings2.DESCRIPTION;
                CursedFuror.this.initializeDescription();
                CursedFuror.this.baseMagicNumber = 3;
                CursedFuror.this.magicNumber = CursedFuror.this.baseMagicNumber;
                CursedFuror.this.upgradedMagicNumber = true;
                CursedFuror.this.cardsToPreview = new NaterranGreatTree();
            }
        });
        return list;
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new CursedFuror();
    }
}

