package shadowverse.cards.Necromancer.Mech;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import rs.lazymankits.interfaces.cards.BranchableUpgradeCard;
import rs.lazymankits.interfaces.cards.UpgradeBranch;
import shadowverse.action.NecromanceAction;
import shadowverse.action.ReanimateAction;
import shadowverse.cards.Neutral.Status.EvolutionPoint;
import shadowverse.cards.Neutral.Temp.*;
import shadowverse.characters.AbstractShadowversePlayer;
import shadowverse.characters.Necromancer;

import java.util.ArrayList;
import java.util.List;


public class FriendsForever
        extends CustomCard implements BranchableUpgradeCard {
    public static final String ID = "shadowverse:FriendsForever";
    public static CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("shadowverse:FriendsForever");
    public static CardStrings cardStrings2 = CardCrawlGame.languagePack.getCardStrings("shadowverse:FriendsForever2");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/FriendsForever.png";

    public FriendsForever() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, CardType.SKILL, Necromancer.Enums.COLOR_PURPLE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.isEthereal = true;
        this.cardsToPreview = (AbstractCard)new GenerateNine();
    }


    public void upgrade() {
        ((UpgradeBranch) ((BranchableUpgradeCard) this).possibleBranches().get(chosenBranch())).upgrade();
    }


    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        switch (chosenBranch()){
            case 0:
                ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
                stanceChoices.add(new LunaGame());
                stanceChoices.add(new AeneaFriendship());
                AbstractGameAction[] gameActions = new AbstractGameAction[]{
                        new ReanimateAction(2),
                        new ReanimateAction(1),
                        new ReanimateAction(0),
                        new HealAction(abstractPlayer, AbstractDungeon.player, 4),
                        new MakeTempCardInHandAction(this.cardsToPreview.makeStatEquivalentCopy()),
                        new SFXAction("FriendsForever")
                };
                addToBot(new NecromanceAction(4, new ChooseOneAction(stanceChoices), gameActions));
                break;
            case 1:
                new SFXAction("FriendsForever2");
                addToBot(new MakeTempCardInHandAction(new ProductMachine(),2));
                int count = 0;
                for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisCombat){
                    if (c.hasTag(AbstractShadowversePlayer.Enums.MACHINE)){
                        count++;
                    }
                }
                if (count >= 10){
                    addToBot(new HealAction(abstractPlayer,abstractPlayer,3));
                    addToBot(new MakeTempCardInHandAction(new EvolutionPoint()));
                    addToBot(new MakeTempCardInHandAction(new Miracle()));
                }
                break;
        }
    }


    public AbstractCard makeCopy() {
        return (AbstractCard) new FriendsForever();
    }

    @Override
    public List<UpgradeBranch> possibleBranches() {
        ArrayList<UpgradeBranch> list = new ArrayList<UpgradeBranch>();
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++FriendsForever.this.timesUpgraded;
                FriendsForever.this.upgraded = true;
                FriendsForever.this.name = cardStrings.NAME + "+";
                FriendsForever.this.initializeTitle();
                FriendsForever.this.isEthereal = false;
                FriendsForever.this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            }
        });
        list.add(new UpgradeBranch() {
            @Override
            public void upgrade() {
                ++FriendsForever.this.timesUpgraded;
                FriendsForever.this.upgraded = true;
                FriendsForever.this.name = cardStrings2.NAME;
                FriendsForever.this.initializeTitle();
                FriendsForever.this.upgradeBaseCost(0);
                FriendsForever.this.cardsToPreview = new ProductMachine();
                FriendsForever.this.rawDescription = cardStrings2.DESCRIPTION;
                FriendsForever.this.initializeDescription();
            }
        });
        return list;
    }
}

