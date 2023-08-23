package shadowverse.cards.Neutral.Neutral;


import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.cards.AbstractRightClickCard;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.powers.FlameNGlassPower;;

import java.util.ArrayList;
import java.util.List;


public class FlameNGlass2 extends AbstractRightClickCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:FlameNGlass2";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:FlameNGlass2");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:FlameNGlass3");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/FlameNGlass2.png";
    public static final String IMG_PATH2 = "img/cards/FlameNGlass3.png";
    private boolean hasFusion = false;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private ArrayList<String> fusionBox = new ArrayList<>();
    private boolean branch2;

    public FlameNGlass2() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, CardType.ATTACK, CardColor.COLORLESS, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 42;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        ((UpgradeBranch)((BranchableUpgradeCard)this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if (AbstractDungeon.getCurrRoom().monsters != null){
            addToBot(new VFXAction(new BlizzardEffect(2, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        }
        addToBot(new DamageAction(abstractMonster, new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("FlameNGlass2"));
                if (this.fusionBox.size()>1){
                    addToBot(new GainEnergyAction(3));
                }
                break;
            case 1:
                addToBot(new SFXAction("FlameNGlass3"));
                addToBot(new GainBlockAction(abstractPlayer,this.block));
                break;
        }
    }

    @Override
    public void triggerOnExhaust() {
        if (branch2){
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FlameNGlassPower(AbstractDungeon.player, 1), 1));
        }
    }

    public AbstractCard makeCopy() {
        return (AbstractCard) new FlameNGlass2();
    }

    @Override
    protected void onRightClick() {
        if (!branch2 && !this.hasFusion && AbstractDungeon.player!=null){
            addToBot(new SelectCardsInHandAction(8,TEXT[0],true,true, card -> {
                return card instanceof Flame || card instanceof Glass;
            }, abstractCards -> {
                for (AbstractCard c:abstractCards){
                    addToBot(new ExhaustSpecificCardAction(c,AbstractDungeon.player.hand));
                    if (!this.fusionBox.contains(c.cardID))
                        fusionBox.add(c.cardID);
                }
                if (abstractCards.size()>0){
                    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                        addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                        addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    }
                }
            }));
            this.hasFusion = true;
        }
    }

    @Override
    public void atTurnStart(){
        hasFusion = false;
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++FlameNGlass2.this.timesUpgraded;
                FlameNGlass2.this.upgraded = true;
                FlameNGlass2.this.name = NAME + "+";
                FlameNGlass2.this.initializeTitle();
                FlameNGlass2.this.damage = 52;
                FlameNGlass2.this.upgradedDamage = true;
                FlameNGlass2.this.baseMagicNumber = 3;
                FlameNGlass2.this.magicNumber = FlameNGlass2.this.baseMagicNumber;
                FlameNGlass2.this.upgradedMagicNumber = true;
                initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++FlameNGlass2.this.timesUpgraded;
                FlameNGlass2.this.upgraded = true;
                FlameNGlass2.this.baseDamage = 18;
                FlameNGlass2.this.upgradedDamage = true;
                FlameNGlass2.this.baseBlock = 18;
                FlameNGlass2.this.upgradedBlock = true;
                FlameNGlass2.this.exhaust = true;
                FlameNGlass2.this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
                FlameNGlass2.this.textureImg = IMG_PATH2;
                FlameNGlass2.this.loadCardImage(IMG_PATH2);
                FlameNGlass2.this.name = cardStrings2.NAME;
                FlameNGlass2.this.initializeTitle();
                FlameNGlass2.this.rawDescription = cardStrings2.DESCRIPTION;
                FlameNGlass2.this.initializeDescription();
                FlameNGlass2.this.branch2 = true;
            }
        });

        return list;
    }
}

