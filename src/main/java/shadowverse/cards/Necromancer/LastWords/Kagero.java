package shadowverse.cards.Necromancer.LastWords;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.NecromanceAction;
import shadowverse.cards.Neutral.Temp.FirstOne;
import shadowverse.cards.Neutral.Temp.SoulStrike;
import shadowverse.cards.Neutral.Temp.Soulflash;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;
import shadowverse.powers.Cemetery;

import java.util.ArrayList;
import java.util.List;

public class Kagero extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:Kagero";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:Kagero");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:Kagero2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/Kagero.png";
    private int previewBranch;

    private static AbstractCard soulStrike = new SoulStrike();
    private static AbstractCard upgradedsouStrike(){
        AbstractCard c = new SoulStrike();
        c.upgrade();
        return c;
    }

    private static AbstractCard soulFlash = new Soulflash();

    public Kagero() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, CardType.ATTACK, Necromancer.Enums.COLOR_PURPLE, CardRarity.RARE, CardTarget.SELF);
        this.baseBlock = 8;
        this.tags.add(AbstractShadowversePlayer.Enums.LASTWORD);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }


    public void upgrade() {
        ((UpgradeBranch)((BranchableUpgradeCard)this).possibleBranches().get(chosenBranch())).upgrade();
    }

    public void update() {
        super.update();
        if (this.previewBranch == 0) {
            if (this.upgraded) {
                this.cardsToPreview = upgradedsouStrike();
            } else
                this.cardsToPreview = soulStrike;
        }else if (this.previewBranch == 1){
            this.cardsToPreview = soulFlash;
        }
    }

    @Override
    public void triggerOnExhaust() {
        if (chosenBranch() == 0){
            if (upgraded){
                addToBot(new NecromanceAction(3,null,new MakeTempCardInHandAction(soulStrike.makeStatEquivalentCopy())));
            }else {
                addToBot(new NecromanceAction(3,null,new MakeTempCardInHandAction(upgradedsouStrike().makeStatEquivalentCopy())));
            }
        }else {
            addToBot(new NecromanceAction(3,null,new MakeTempCardInHandAction(soulFlash.makeStatEquivalentCopy())));
        }
    }

    @Override
    public void triggerWhenDrawn(){
        if (previewBranch == 0){
            int playerNecromance = 0;
            if (AbstractDungeon.player.hasPower(Cemetery.POWER_ID)){
                for (AbstractPower p :AbstractDungeon.player.powers){
                    if (p.ID.equals(Cemetery.POWER_ID))
                        playerNecromance = p.amount;
                }
            }
            if (playerNecromance>=10){
                for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                    addToBot(new ApplyPowerAction(mo, AbstractDungeon.player, (AbstractPower)new VulnerablePower(mo, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        switch (chosenBranch()){
            case 0:
                addToBot(new SFXAction("Kagero"));
                break;
            case 1:
                addToBot(new SFXAction("Kagero2"));
                break;
        }
        addToBot(new GainBlockAction(AbstractDungeon.player,this.block));
    }

    public AbstractCard makeCopy() {
        return new Kagero();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Kagero.this.timesUpgraded;
                Kagero.this.upgraded = true;
                Kagero.this.name = cardStrings.NAME + "+";
                Kagero.this.initializeTitle();
                Kagero.this.baseBlock = 11;
                Kagero.this.upgradedBlock = true;
                Kagero.this.baseMagicNumber = 2;
                Kagero.this.magicNumber = Kagero.this.baseMagicNumber;
                Kagero.this.upgradedMagicNumber = true;
                Kagero.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                Kagero.this.initializeDescription();
                Kagero.this.previewBranch = 0;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++Kagero.this.timesUpgraded;
                Kagero.this.upgraded = true;
                Kagero.this.name = cardStrings2.NAME;
                Kagero.this.initializeTitle();
                Kagero.this.rawDescription = cardStrings2.DESCRIPTION;
                Kagero.this.initializeDescription();
                Kagero.this.previewBranch = 1;
            }
        });
        return list;
    }
}
