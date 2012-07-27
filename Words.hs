module Main
where

import Data.List
import System.IO

main = do contents <- readFile "stopwords.txt"
          let ws = words contents
          let out = intercalate "\n" ws
          writeFile "stopwords.out" out
