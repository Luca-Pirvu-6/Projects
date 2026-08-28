

IF OBJECT_ID('MatchEvents', 'U') IS NOT NULL DROP TABLE MatchEvents;
IF OBJECT_ID('Matches', 'U') IS NOT NULL DROP TABLE Matches;
IF OBJECT_ID('Players', 'U') IS NOT NULL DROP TABLE Players;
IF OBJECT_ID('Seasons', 'U') IS NOT NULL DROP TABLE Seasons;
IF OBJECT_ID('Teams', 'U') IS NOT NULL DROP TABLE Teams;
IF OBJECT_ID('Leagues', 'U') IS NOT NULL DROP TABLE Leagues;

CREATE TABLE Leagues (
                         Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         Name NVARCHAR(100) NOT NULL UNIQUE
);


CREATE TABLE Seasons (
                         Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         Name NVARCHAR(50) NOT NULL,
                         LeagueId BIGINT NOT NULL,
                         StartDate DATE NOT NULL,
                         EndDate DATE NOT NULL,
                         CONSTRAINT FK_Seasons_Leagues FOREIGN KEY (LeagueId) REFERENCES Leagues(Id) ON DELETE CASCADE,
                         CONSTRAINT CHK_Season_Dates CHECK (EndDate > StartDate)
);


CREATE TABLE Teams (
                       Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                       Name NVARCHAR(100) NOT NULL UNIQUE,
                       City NVARCHAR(100) NOT NULL,
                       ShortCode NVARCHAR(10) NOT NULL UNIQUE
);


CREATE TABLE Players (
                         Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         FirstName NVARCHAR(50) NOT NULL,
                         LastName NVARCHAR(50) NOT NULL,
                         BirthDate DATE NOT NULL,
                         Position NVARCHAR(20) NOT NULL,
                         ShirtNumber INT NOT NULL,
                         TeamId BIGINT NULL,
                         CONSTRAINT FK_Players_Teams FOREIGN KEY (TeamId) REFERENCES Teams(Id) ON DELETE SET NULL,
                         CONSTRAINT CHK_ShirtNumber CHECK (ShirtNumber > 0 AND ShirtNumber <= 99),
                         CONSTRAINT CHK_Position CHECK (Position IN ('GOALKEEPER', 'DEFENDER', 'MIDFIELDER', 'FORWARD'))
);


CREATE TABLE Matches (
                         Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                         SeasonId BIGINT NOT NULL,
                         HomeTeamId BIGINT NOT NULL,
                         AwayTeamId BIGINT NOT NULL,
                         MatchDate DATETIME2 NOT NULL,
                         HomeScore INT NOT NULL DEFAULT 0,
                         AwayScore INT NOT NULL DEFAULT 0,
                         Status NVARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
                         CONSTRAINT FK_Matches_Seasons FOREIGN KEY (SeasonId) REFERENCES Seasons(Id),
                         CONSTRAINT FK_Matches_HomeTeam FOREIGN KEY (HomeTeamId) REFERENCES Teams(Id),
                         CONSTRAINT FK_Matches_AwayTeam FOREIGN KEY (AwayTeamId) REFERENCES Teams(Id),
                         CONSTRAINT CHK_Distinct_Teams CHECK (HomeTeamId <> AwayTeamId),
                         CONSTRAINT CHK_Match_Status CHECK (Status IN ('SCHEDULED', 'IN_PROGRESS', 'FINISHED', 'POSTPONED', 'CANCELLED')),
                         CONSTRAINT CHK_Scores CHECK (HomeScore >= 0 AND AwayScore >= 0)
);


CREATE TABLE MatchEvents (
                             Id BIGINT IDENTITY(1,1) PRIMARY KEY,
                             MatchId BIGINT NOT NULL,
                             PlayerId BIGINT NOT NULL,
                             AssistPlayerId BIGINT NULL,
                             EventType NVARCHAR(30) NOT NULL,
                             Minute INT NOT NULL,
                             CONSTRAINT FK_MatchEvents_Matches FOREIGN KEY (MatchId) REFERENCES Matches(Id) ON DELETE CASCADE,
                             CONSTRAINT FK_MatchEvents_Player FOREIGN KEY (PlayerId) REFERENCES Players(Id),
                             CONSTRAINT FK_MatchEvents_AssistPlayer FOREIGN KEY (AssistPlayerId) REFERENCES Players(Id),
                             CONSTRAINT CHK_Event_Minute CHECK (Minute >= 1 AND Minute <= 120),
    CONSTRAINT CHK_Event_Assist CHECK (AssistPlayerId IS NULL OR AssistPlayerId <> PlayerId),
    CONSTRAINT CHK_EventType CHECK (EventType IN (
        'GOAL', 'PENALTY_GOAL', 'OWN_GOAL', 'YELLOW_CARD',
        'RED_CARD', 'CORNER', 'FREE_KICK', 'HANDBALL', 'OFFSIDE'
    ))
);


CREATE INDEX IX_Matches_SeasonId ON Matches(SeasonId);
CREATE INDEX IX_Matches_HomeTeam ON Matches(HomeTeamId);
CREATE INDEX IX_Matches_AwayTeam ON Matches(AwayTeamId);
CREATE INDEX IX_MatchEvents_MatchId ON MatchEvents(MatchId);
CREATE INDEX IX_MatchEvents_PlayerId ON MatchEvents(PlayerId);
CREATE INDEX IX_Players_TeamId ON Players(TeamId);