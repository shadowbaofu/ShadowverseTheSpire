package shadowverse.cards.Necromancer.Shadows;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HeartBuffEffect;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.characters.Necromancer;
import shadowverse.powers.Cemetery;
import shadowverse.powers.Minthe2Power;
import shadowverse.powers.MinthePower;

import java.util.ArrayList;
import java.util.List;


public class Minthe
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Minthe";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Minthe");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Minthe2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Minthe.png";
    public static final String IMG_PATH2 = "img/cards/Minthe2.png";

    public Minthe() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.POWER, Necromancer.Enums.COLOR_PURPLE, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.isEthereal = true;
    }


    public void upgrade() {
        ((UpgradeBranch)((BranchableUpgradeCard)this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("Minthe"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(abstractPlayer.hb.cX, abstractPlayer.hb.cY)));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new Cemetery(abstractPlayer, 20), 20));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new MinthePower(abstractPlayer, this.magicNumber), this.magicNumber));
                break;
            case 1:
                addToBot(new SFXAction("Minthe2"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new HeartBuffEffect(abstractPlayer.hb.cX, abstractPlayer.hb.cY)));
                addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new Minthe2Power(abstractPlayer)));
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new Minthe();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Minthe.this.timesUpgraded;
                Minthe.this.upgraded = true;
                Minthe.this.name = NAME + "+";
                Minthe.this.initializeTitle();
                Minthe.this.isEthereal = false;
                Minthe.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                initializeDescription();
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Minthe.this.timesUpgraded;
                Minthe.this.upgraded = true;
                Minthe.this.textureImg = IMG_PATH2;
                Minthe.this.loadCardImage(IMG_PATH2);
                Minthe.this.name = cardStrings2.NAME;
                Minthe.this.initializeTitle();
                Minthe.this.rawDescription = cardStrings2.DESCRIPTION;
                Minthe.this.initializeDescription();
            }
        });

        return list;
    }
}
