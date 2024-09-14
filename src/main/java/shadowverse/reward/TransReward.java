package shadowverse.reward;

import basemod.abstracts.CustomReward;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import shadowverse.cards.Dragon.Armed.Rola;
import shadowverse.cards.Necromancer.Default.Ceridwen;
import shadowverse.cards.Nemesis.Artifact.Modest;
import shadowverse.cards.Nemesis.Artifact.SisterlyBonds;
import shadowverse.cards.Nemesis.Condemned.Invasion;
import shadowverse.cards.Nemesis.Condemned.Judith;
import shadowverse.cards.Nemesis.Default.*;
import shadowverse.cards.Nemesis.Mech.AerialCraft;
import shadowverse.cards.Nemesis.Mech.BelphometCard;
import shadowverse.cards.Nemesis.Mech.Enforcer;
import shadowverse.cards.Nemesis.Mech.MegaEnforcer;
import shadowverse.cards.Nemesis.Pile.*;
import shadowverse.cards.Nemesis.Puppet.*;
import shadowverse.cards.Nemesis.Resonance.Metaproduction;
import shadowverse.cards.Nemesis.Tokens.*;
import shadowverse.characters.*;

import java.util.ArrayList;

public class TransReward  extends CustomReward {
    public static final String ID = "shadowverse:TransReward";

    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString("shadowverse:ClassReward")).TEXT;
    public CardGroup group;

    public TransReward() {
        super(ImageMaster.loadImage("img/reward/placeholder.png"), TEXT[0], RewardType.CARD);
        this.group = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        AbstractPlayer.PlayerClass cl = AbstractDungeon.player.chosenClass;
        this.group.addToBottom(new MindDivider());
        this.group.addToBottom(new Judith());
        this.group.addToBottom(new UnnamedDetermination());
        this.group.addToBottom(new Maisha());
        if(cl== Elf.Enums.Elf){
            this.group.addToBottom(new RevisedExperiment());
            this.group.addToBottom(new Scavenge());
            this.group.addToBottom(new PuppeteerStrings());
            this.group.addToBottom(new SisterlyBonds());
            this.group.addToBottom(new StringMaster());
            this.group.addToBottom(new Spine());
            this.group.addToBottom(new DollsOwner());
            this.group.addToBottom(new InfinityPuppeteer());
            this.group.addToBottom(new Licht());
            this.group.addToBottom(new Noa());
            this.group.addToBottom(new Orchid());
            this.group.addToBottom(new PuppetRoom());
            this.group.addToBottom(new TriggerPuppeteer());
            this.group.addToBottom(new Zwei());
            this.group.addToBottom(new HeartlessBattle());
            this.group.addToBottom(new Windup());
        }else if(cl== Royal.Enums.Royal){
            this.group.addToBottom(new Ralmia());
        }else if(cl== Witchcraft.Enums.WITCHCRAFT){
            this.group.addToBottom(new Ceridwen());
            this.group.addToBottom(new ApostleOfDestruction());
            this.group.addToBottom(new Paracelsus());
            this.group.addToBottom(new ReturningMelody());
            this.group.addToBottom(new Metaproduction());
            this.group.addToBottom(new DiscipleOfDestruction());
            this.group.addToBottom(new AerialCraft());
            this.group.addToBottom(new BelphometCard());
            this.group.addToBottom(new MegaEnforcer());
            this.group.addToBottom(new Enforcer());
            this.group.addToBottom(new Invasion());
            this.group.addToBottom(new IronStaffMechanic());
            this.group.addToBottom(new AdherentOfMelody());
            this.group.addToBottom(new DestructionRefrain());
            this.group.addToBottom(new OmenOfDestruction());
        }else if(cl== Dragon.Enums.Dragon){
            this.group.addToBottom(new SyntheticEden());
            this.group.addToBottom(new NewOrder());
            this.group.addToBottom(new Tolerance());
            this.group.addToBottom(new Modest());
            this.group.addToBottom(new Chastity());
        }else if(cl== Necromancer.Enums.Necromancer){
            for (AbstractCard q : CardLibrary.getAllCards()) {
                if (q.color == Vampire.Enums.COLOR_SCARLET) {
                    this.group.addToBottom(q.makeCopy());
                }
            }
            this.group.addToBottom(new ICCard());
            this.group.addToBottom(new Illganeau());
        }else if(cl== Vampire.Enums.Vampire){
            for (AbstractCard q : CardLibrary.getAllCards()) {
                if (q.color == Necromancer.Enums.COLOR_PURPLE) {
                    this.group.addToBottom(q.makeCopy());
                }
            }
            this.group.addToBottom(new ICCard());
            this.group.addToBottom(new Illganeau());
        }else if(cl== Bishop.Enums.Bishop){
            this.group.addToBottom(new Rola());
            this.group.addToBottom(new Concentrate());
            this.group.addToBottom(new Karula());
            this.group.addToBottom(new TheWheelOfFortune());
            this.group.addToBottom(new AugmentationBestowal());
            this.group.addToBottom(new DeusExMachina());
        }
        this.cards.clear();
        this.cards.addAll(getCards());
    }

    public ArrayList<AbstractCard> getCards() {
        if(this.group.size()<3){
            return this.group.group;
        }
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        while (cardsList.size() < 3) {
            AbstractCard card = this.group.getRandomCard(true, AbstractDungeon.getCurrRoom().getCardRarity(AbstractDungeon.cardRandomRng.random(100)));
            if (!cardListDuplicate(cardsList, card)) {
                cardsList.add(card);
            }
        }
        return cardsList;
    }

    public boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean claimReward() {
        if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.COMBAT_REWARD) {
            AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[0]);
            AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        }
        return false;
    }
}

