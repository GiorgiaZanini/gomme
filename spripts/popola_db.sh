sudo apt install python3.12-venv
rm -rf .venv
python3 -m venv .venv
source .venv/bin/activate
pip install xlrd requests
python3 importa_excel.py database_v04_OLD.xls

# run: bash popola_db.sh